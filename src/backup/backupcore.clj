(ns ecommerce.backupcore
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [ecommerce.db :as db]
            [ecommerce.model :as model]
            ))
(def conn (db/abre-conexao!))

(db/cria-schema conn)

(let [computador
      (model/novo-produto "Computador novo", "/computador_novo", 2500.10M)]
  (d/transact conn [computador]))

(def db (d/db conn))                                        ;ro banco no instante que executa a linha

; ? = variavel query language datomic datalog
(d/q '[:find ?entidade
       :where [?entidade :produto/nome]] db)

(let [computador
      (model/novo-produto "Computador novo", "/computador_novo", 2500.10M)]
  (d/transact conn [computador]))


(def db (d/db conn))                                        ;novo snapshot do banco
(d/q '[:find ?entidade
       :where [?entidade :produto/nome]] db)

; (d/q '[:find ?entidade :produto/nome
;     :where [?entidade :produto/nome]] db)

;o datomic suporta somente um dos identificadores
(let [calculadora {:produto/nome "Calculadora com 4 operações"}]
  (d/transact conn [calculadora]))

;n funciona quer algo ""vazio só n colocar
; (let [radio-relogio {:produto/nome "radio com relogio" :produto/slug nil}]
; (d/transact conn [radio-relogio]))

(let [celular-barato (model/novo-produto "Celular Barato", "/celular-barato", 8888.10M)
      resultado @(d/transact conn [celular-barato])
      ;versao menos otimizada    id-identidade (first (vals (:tempids resultado)))
      id-identidade (-> resultado :tempids vals first)]
  (pprint resultado)
  (pprint @(d/transact conn [[:db/add id-identidade :produto/preco 0.1M]]))
  (pprint @(d/transact conn [[:db/retract id-identidade :produto/slug "/celular-barato"]])))

(db/apaga-banco!)
