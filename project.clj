(defproject tiny "1.0.0-SNAPSHOT"
  :description "Url shortener service"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [compojure "1.0.0"]
                 [hiccup "0.3.7"]
                 [lein-ring "0.5.0"]
                 [postgresql/postgresql "8.4-702.jdbc4"]
                 [org.clojure/java.jdbc "0.1.1"]]
  :ring {:handler compojure.example.routes/app})