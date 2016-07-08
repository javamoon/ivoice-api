(ns ivoice-api.korma-demo
  (:require [korma.db :refer :all]
            [korma.core :as db :refer [defentity table database insert select values 
            						   where set-fields has-many many-to-many belongs-to 
            						   with fields]]
            [ivoice-api.cfg :refer :all])
  (:import [java.util UUID Date]))

(defdb dbcfg
  (mysql (:dbcfg cfg)))

(defn uuid [] (.toString (UUID/randomUUID)))

(defn now [] (Date.))

(declare users resource content media cover users2)

(defentity users (has-many resource {:fk :uid})
				 (many-to-many users2 :users_follow
				 			   {:lfk :uid :rfk :follow_id}))
(defentity users2 (table :users :users2)
				  (many-to-many users :users_follow ))

(defentity content)

(defentity media (table :content :media)
				 (has-many resource))

(defentity cover (table :content :cover) 
				 (has-many resource))

(defentity resource 
  (belongs-to users {:fk :uid})
  (belongs-to media {:fk :media_id}) 
  (belongs-to cover {:fk :cover_id}))

(defn content-insert 
  "{:generated_key 3}"
  [{:keys [created length location name postfix mine type status ucode]}]
  (insert content
  		  (values {:mine mine
  		  		   :length length
  		  		   :type type
  		  		   :status status
  		  		   :postfix postfix
  		  		   :name name
  		  		   :location location
  		  		   :ucode ucode
  		  		   :created created
  		  		   }) ) )


(defn resource-insert 
  "{:generated_key <id>}"
  [{:keys [created media_id cover_id title 
  		   description alaya uid visits 
  		   favor download album_id status 
  		   scores group ucode]}]
  (insert resource
  		  (values {:title title
  		  		   :description description
  		  		   :alaya alaya
  		  		   :uid uid
  		  		   :media_id media_id
  		  		   :cover_id cover_id
  		  		   :album_id album_id
  		  		   :ucode ucode
  		  		   :created created
  		  		   :status status
  		  		   :visits visits
  		  		   :favor favor
  		  		   :download download
  		  		   :scores scores
  		  		   :group group})))

(defn select-demo [] 
	(select users (with users2 (fields :phone :nickname :ucode)) (fields :id) (where {:id 1}))
	(select users (with resource (fields :title)) (fields :id) (where {:id 2}))
	(select resource (with users) 
					 (with media) 
					 (with cover) 
		    		 (fields :title :ucode :users.nickname :users.phone 
		    		 	     [:media.type :media-type] [:cover.type :cover-type] 
		    		 	     [:media.name :media-name] [:cover.name :cover-name])) )

(defn insert-demo []
	(insert users (values {:phone "13812341234" :nickname "lotus" :status 1 :ucode (.toString (UUID/randomUUID)) :created (Date.)}))
	(insert content (values {:length 123 :name "test" :status 1}))
	(insert resource (values {:media_id 1 :uid 2 :title "TEST title" :ucode (uuid)})) )

(defn insert-users [m] (insert users (values m)))