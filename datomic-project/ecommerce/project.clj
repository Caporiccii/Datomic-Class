(defproject ecommerce "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.datomic/datomic-pro "0.9.5951"]]
  :exclusions [log4j]
  :plugins [[s3-wagon-private "1.3.5"]]
  :repl-options {:init-ns ecommerce.core})
