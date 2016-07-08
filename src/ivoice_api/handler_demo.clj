(ns ivoice-api.handler-demo
  (:require [ring.util.response :refer :all]
      	  	[clojure.data.json :as json]
      	  	[clojure.java.io :as io]
            [ivoice-api.util :refer :all]
            [ivoice-api.upload :refer :all]))

(defn total [] 10)

(def video-url "")

(def image-path "")

(defn handler [req] (response (to-json {:handler "demo"})))

(defn upload-handler-demo [req] 
  (upload req) 
  (response (to-json {:code 1
                      :data {:contentId 622}})))

(defn save-handler [req] 
  (response (to-json {:code 1 :data {}})))

(defn load-handler [req]
  (let [v {:code 1
             :data {:id 624
                    :title "标题"
                    :cover_id 623
                    :cover_address (str image-path "0.jpg")
                    :album_id 1
                    :album_name "默认专辑"
                    :description "描述。。。"
              }}]
    (response (to-json v))
              ))

(defn uploadPage-handler-demo [req] 
  (let [v {:code 1
             :data {:resourceId 624}}]
  (response (to-json v))))

(defn demo-handler [r] (-> (response "Demo")
                           (set-cookie "hello" "world" {:max-age 60})
                           (set-cookie "k11" "v22" {:max-age 100})
                           ))

(defn jsonMap4play [n]
  (let [v ""
        a ""
        video? (fn [n]
                 (let [x (mod n 2)]
                   (if (= x 0) true false)))]
    {:code 1
     :data {:id 628
            :title "标题"
            :cover_id 623
            :cover_address (str image-path "0.jpg")
            :album_id 1
            :album_name "默认专辑"
            :description "描述。。。"
            :author ""
            :avatar (str image-path "0.jpg")
            :type (if (video? n) 1 2)
            :create "2016-06-28"
            :resource_address (if (video? n) v a)}
     :text "mock test"}))

(defn play-handler 
  [{{id :id} :params}]
  (let [parse (fn [id] (if id 
                         (let [n (Integer/valueOf id)]
                           (jsonMap4play n))
                         {:code 0
                          :data {}
                          :text "absence params!"}))
        body (parse id)]
    (response (to-json body))
  ))

(defn map4josn 
  [p s] 
  (let [page (Integer/valueOf p)
        size (Integer/valueOf s)
        hot (fn [x] 
            (let [rn (rand-int 20)
                  alaya (if (= 0 (rand-int 6)) 1 0)]
              {:id x
               :cover (str image-path (if (= 1 alaya) 0 rn) ".jpg")
               :description (str (if (= 1 alaya) "[音频]" "[视频]") " 测试内容 TEST I声音 TEST" x)
               :watch (rand-int 1000)
               :favour (rand-int 1000)
               :alaya alaya 
               :url video-url}))
        v (for [x (range size)] (hot (+ (* page size) (inc x))))
        m {:code 1
           :data {:resultSet {}
                  :current page
                  :size size
                  :total (total)
                  :next (inc page)}
           :text "mock test"}]
  (assoc-in m [:data :resultSet] v)))

(defn hostpot-handler 
  [{{:keys [page size] 
     :or {page "0" size "6"}} 
    :params}]
  (let [v (map4josn page size)]
    (prn v)
    (response (to-json v))))

;; album
(defn album-save-handler [req]
  (-> response-body
      (assoc-in [:data :albumId] 123)
      to-json
      response))

(defn album-privylist-handler [req]
  (-> response-body
      (assoc-in [:data :resultSet] [])
      (assoc-in [:data :resultSet 0] {:id 1 :title "专辑1"})
      (assoc-in [:data :resultSet 1] {:id 2 :title "专辑2"})
      to-json
      response))

#_(defn upload-handler2 [req] 
  (-> (if-let [m (upload-extract req)]
        (let [fm (upload m)
            contentId (save-content-service (merge m fm))]
          (assoc-in response-body [:data :contentId] contentId))
        response-error)
      to-json
      response))

#_(defn uploadPage-handler2 [req] 
  (-> (if-let [m (upload-extract req)]
        (let [fm (upload m)
            resourceId (sava-resource-service (merge m fm))]
          (assoc-in response-body [:data :resourceId] resourceId))
        response-error)
      to-json
      response))
