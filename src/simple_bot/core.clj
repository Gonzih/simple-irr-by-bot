(ns simple-bot.core
  (:require [simple-bot.memoization :as memo]))

(defn -main [& args]
  (loop []
    (Thread/sleep (* 1000 60 10))
    (memo/check-for-new)
    (recur)))
