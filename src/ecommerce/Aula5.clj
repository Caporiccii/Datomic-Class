(ns ecommerce.Aula5
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [ecommerce.db :as db]
            [ecommerce.model :as model]))

(def conn (db/abre-conexao!))

(db/cria-schema! conn)

(def eletronicos (model/nova-categoria "Eletrônicos"))
(def esporte (model/nova-categoria "Esporte"))

(pprint @(db/adiciona-categorias! conn [eletronicos esporte]))

(def categorias (db/todas-as-categorias (d/db conn)))
(pprint categorias)

(def computador (model/novo-produto (model/uuid) "Computador Novo", "/computador-novo", 2500.10M))
(def celular (model/novo-produto (model/uuid) "Celular Caro", "/celular", 888888.10M))
(def calculadora {:produto/nome "Calculadora com 4 operações"})
(def celular-barato (model/novo-produto "Celular Barato", "/celular-barato", 0.1M))
(def celular-barato-2 (model/novo-produto (:produto/id celular-barato) "celular barato", "celular varato", 0.0001M))
(def xadrez (model/novo-produto  "Tabuleiro de Xadrez", "/tabuleiro-de-xadrez", 30M))
;quando uso um id que já existe não cria um novo ele atualiza
(pprint @(db/adiciona-produtos! conn [computador, celular, calculadora, celular-barato, xadrez]))

(db/atribui-categorias! conn [computador, celular, celular-barato] eletronicos)

(db/atribui-categorias! conn [xadrez] esporte)

;podemos fazer db/add com nested maps (aninhados)
(pprint @(db/adiciona-produtos! conn [{:produto/nome "Camiseta"
                                       :produto/slug     "/camiseta"
                                       :produto/preco     30M
                                       :produto/id        (model/uuid)
                                       :produto/categoria {:categoria/nome "Roupas"
                                                           :categoria/id (model/uuid)}}]))

(def esporte-id (:categoria/id esporte))                    ;lookup do uuid categoria esporte
(pprint @(db/adiciona-produtos! conn [{:produto/nome "Xadrez"
                                       :produto/slug     "/xadrez"
                                       :produto/preco     30M
                                       :produto/id        (model/uuid)
                                       :produto/categoria [:categoria/id esporte-id]}]))
(println (db/todos-os-nomes-de-produtos-e-categorias (d/db conn)))
(println (db/resumo-dos-produtos (d/db conn)))
(println (db/resumo-dos-produtos-por-categoria (d/db conn)))
;(db/apaga-banco!)
