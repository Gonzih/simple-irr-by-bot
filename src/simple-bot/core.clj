(ns simple-bot.core
  (:require [simple-bot.memoization :as memo]))

(defn -main [& args]
  (memo/check-for-new))
