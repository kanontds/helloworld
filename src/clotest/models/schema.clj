(ns clotest.models.schema
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def datapath (str (System/getProperty "user.dir") "/"))

(defn- keywordize [s]
  (-> (str/lower-case s)
      (str/replace "_" "-")
      (str/replace "." "-")
      (keyword)))

(defn- sanitize [k]
  (let [s (keywordize (name k))]
    (if-not (= k s) (println "Warning: environ key " k " has been corrected to " s))
    s))

(defn test-db []
  (let [env-file (io/file (str datapath "/src/clotest/models/db-env"))]
    (if (.exists env-file)
      (into {} (for [[k v] (read-string (slurp env-file))]
                 [(sanitize k) v])))))

;;oracle 连接
(def db-oracle  {:classname "oracle.jdbc.OracleDriver"
                 :subprotocol "oracle"
;                 :subname "thin:@121.40.143.127:1521:orcl"
                 :subname    (str "thin:@" (if (> (count (:subname (test-db))) 0)  (:subname (test-db)) "192.168.2.142")  ":1521:orcl")                                 ;"thin:@192.168.2.142:1521:orcl"
                 :user       (if (> (count (:user (test-db))) 0) (:user (test-db))  "NEWPENSION_SYSTEM")                                 ;"NEWPENSION_SYSTEM"
                 :password   (if (> (count (:password (test-db))) 0)  (:password (test-db)) "hvit")                               ;"hvit"
                 :naming {:keys clojure.string/lower-case :fields clojure.string/upper-case}})


;;postgres 连接
(def db-postgres  {:classname "org.postgresql.Driver" ; must be in classpath
                   :subprotocol "postgresql"
                   :subname "//192.168.2.141:5432/dymap"
                   :user "postgres"
                   :password "hvit"}
  )


