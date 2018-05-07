(ns my-exercise.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.reload :refer [wrap-reload]]
            [my-exercise.home :as home]))

(defn search [x]
  (println x)
  "response")

(defn lcp [form field]
  (clojure.string/lower-case (clojure.string/replace (str (get form field)) " " "_"))
)

(defn searchAddress [form]
  (str "https://api.turbovote.org/elections/upcoming?district-divisions=ocd-division/country:us/state:" (lcp form "state") ",ocd-division/country:us/state:" (lcp form "state") "/place:" (lcp form "city"))
)

; Thanks for the opportunity to work on this. I've never used clojure before, so this is as far as I could get without going way over 2 hours.

; (defn searchAddress [form]
;   (response {:result (client/get (str "https://api.turbovote.org/elections/upcoming?district-divisions=ocd-division/country:us/state:" (lcp form "state") ",ocd-division/country:us/state:" (lcp form "state") "/place:" (lcp form "city")))})
; )

(defroutes app
  (GET "/" [] home/page)
  ; (POST "/search" [x] (search x))
  (POST "/search" request
    (searchAddress (:form-params request)))
  (route/resources "/")
  (route/not-found "Not found"))

(def handler
  (-> app
      (wrap-defaults site-defaults)
      wrap-reload))

