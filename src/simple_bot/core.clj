(ns simple-bot.core
  (:require [simple-bot.memoization :as memo]))

(defn -main [& args]
  (loop []
    (memo/check-for-new)
    (Thread/sleep (* 1000 60 10))
    (recur)))
