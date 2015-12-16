(ns clotest.routes.home
  (:require [clotest.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]

            [selmer.parser :refer [render-file]]
            [clotest.controller.controller :as ctl]))

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defn index-page []
  (render-file "index.html" {:name "index name" :items (range 10)}))

(defn dom-page []
  (layout/render "javascriptDOM.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))

  (GET "/testdb" [] (ctl/test-db))
  (GET "/testindex" [] (index-page))
  (GET "/learndom" [] (dom-page)))


