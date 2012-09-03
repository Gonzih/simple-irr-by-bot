(ns simple-bot.core
  (:use     [clojure.tools.logging :only (info error)])
  (:require [simple-bot.memoization :as memo]))

(def sleep (* 1000 60 60))

(defn -main [& args]
  (loop []
    (memo/check-for-new)
    (info "Waiting...")
    (Thread/sleep sleep)
    (recur)))
