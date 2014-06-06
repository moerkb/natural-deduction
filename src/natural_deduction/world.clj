(ns natural-deduction.core)

(def worlds (atom []))
(def counter (atom 0))

#_(defn build-world
  ([world]
    (build-world world false))
  
  ([world flag]
    (let [f (first world)
          n (next world)
          fl (if flag
               flag
               (= f '⊢))
          b (if (= f '⊢)
              :todo
              f)
          hashset {:body b
                   :hash (when (not= f '⊢) (swap! counter inc))
                   :rule (when (not fl) :premise)}]
      (if (first n)
        (cons
          hashset
          (build-world n fl))
        [hashset]))))

(defn build-world
  [world]
  (let [flag (atom false)]
    (reset! counter 0)
    (clojure.walk/postwalk
      (fn [x]
        (if (vector? x)
          x
          {:body (if (= x '⊢) (do (reset! flag true) :todo) x)
           :hash (when (not= x '⊢) (swap! counter inc))
           :rule (when (not @flag) :premise)}
          ))
      world)))

(defn build-pretty-string
  [elem]
  (let [r (:rule elem)]
    (if (= (:body elem) :todo)
      "..."
      (str (:body elem)
           "\t(#"
           (:hash elem)
           (when r (str "\t" r))
           ")"))))

(defn pretty-printer
  ([world]
    (pretty-printer world 0))
  
  ([world lvl]
    (doseq [elem world]
      (if (vector? elem)
        (do
          (dotimes [_ lvl] (print "| "))
          (dotimes [_ (- 40 lvl)] (print "--"))
          (println)
          (pretty-printer elem (inc lvl))
          (dotimes [_ lvl] (print "| "))
          (dotimes [_ (- 40 lvl)] (print "--"))
          (println))
        (do
          (dotimes [_ lvl] (print "| "))
          (println (build-pretty-string elem)))))))

(defn undo!
  []
  (do
    (when (second @worlds) (reset! worlds (vec (drop-last @worlds))))
    (pretty-printer (last @worlds))))

(defn set-world!
  [new-world]
  (do
    (reset! counter 0)
    (reset! worlds [(vec (build-world new-world))])
    (pretty-printer (last @worlds))))
  