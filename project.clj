(defproject irr_by_notify "0.1.0-SNAPSHOT"
  :description "Simple Clojure bot for irr.by ads."
  :url "https://github.com/Gonzih/simple-irr-by-bot"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [enlive "1.0.1"]
                 [org.clojure/tools.logging "0.2.3"]
                 [digest "1.3.0"]
                 [org.apache.commons/commons-email "1.2"]]
  :main simple-bot.core)
