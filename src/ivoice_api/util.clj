(ns ivoice-api.util
  (:require [ring.util.response :refer :all]
  			[clojure.data.json :as json])
  (:import [java.util UUID Date]
  		   java.time.LocalDateTime
  		   java.time.format.DateTimeFormatter))

(def DATETIME-FORMATTER (DateTimeFormatter/ofPattern "yyyy-MM-dd HH:mm:ss"))

(def response-body {:code 1 :data {} :text ""})

(def response-empty {:code 2 :data {} :text "Result is emppty!"})

(def response-error {:code 0 :data {} :text "params error!"})

(defn respone-result [code text] {:code code :data {} :text text})

(defn to-json [m] (json/json-str m))

(defn uuid [] (.toString (UUID/randomUUID)))

(defn now [] (Date.))

(defn parse-int [s] (try (Integer/valueOf s) (catch Throwable t)))

(defn response-json [resp] (-> resp to-json response))

(defn format-datetime [^LocalDateTime localDateTime] (.format localDateTime DATETIME-FORMATTER))

(defn format-timestamp
  [^java.sql.Timestamp timestamp]
  (if timestamp
  	(-> timestamp
	  	.toLocalDateTime
	  	format-datetime) ) )

