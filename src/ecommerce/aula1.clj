(ns ecommerce.aula1
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [ecommerce.db :as db]
            [ecommerce.model :as model]
            ))

(def conn (db/abre-conexao!))

(db/cria-schema conn)

(let [computador      (model/novo-produto (model/uuid) "Computador Novo", "/computador-novo", 2500.10M)
      celular         (model/novo-produto (model/uuid) "Celular Caro", "/celular", 888888.10M)
      calculadora     {:produto/nome "Calculadora com 4 operações"}
      celular-barato  (model/novo-produto (model/uuid) "Celular Barato", "/celular-barato", 0.1M)]
  (pprint @(d/transact conn [computador, celular, calculadora, celular-barato])))


(pprint (db/todos-os-produtos (d/db conn)))

(def produtos (db/todos-os-produtos (d/db conn)))
(def primeiro-dbid (-> produtos
                             ffirst
                             :db/id))

(pprint (db/um-produto-por-dbid (d/db conn) primeiro-dbid))

(def primeiro-produto-id (-> produtos
                             ffirst
                             :produto/id))

(println "UUID" primeiro-produto-id)
(pprint (db/um-produto (d/db conn) primeiro-produto-id))

(pprint (db/um-produto-por-id (d/db conn) primeiro-dbid))

(pprint (db/um-produto (d/db conn) 17592186045440))

;(db/apaga-banco)
