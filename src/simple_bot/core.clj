(ns simple-bot.core
  (:use     [clojure.tools.logging :only (info error)])
  (:require [simple-bot.memoization :as memo])
  (:import  [java.net UnknownHostException]))

(def sleep (* 1000 60 10))

(defn -main [& args]
  (loop []
    (try
      (memo/check-for-new)
      (catch UnknownHostException e
        (info "Catched UnknownHostException for" (.getMessage e))))
    (info "Waiting...")
    (Thread/sleep sleep)
    (recur)))
