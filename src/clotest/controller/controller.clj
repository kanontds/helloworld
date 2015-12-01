(ns clotest.controller.controller
  (:require  [clotest.models.schema :as schema]
             [noir.response :as resp]))


(defn test-db []
  (let [filedata (schema/test-db)]
    (resp/json {:subname (str "thin:@" (:subname filedata) ":1521:orcl") :user (:user filedata) :password (:password filedata)})))
