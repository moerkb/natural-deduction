(ns natural-deduction.core)

(def worlds (atom []))
(def counter (atom 0))

(defn build-world
  [world flag]
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
      [hashset])))

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

(defn pretty-printer ; TODO: inner beweise darstellen
  [world]
  (doseq [elem world]
    (println (build-pretty-string elem))))

(defn undo
  []
  (do
    (when (second @worlds) (reset! worlds (vec (drop-last @worlds))))
    (pretty-printer (last @worlds))))

(defn set-world!
  [new-world]
  (do
    (reset! counter 0)
    (reset! worlds [(vec (build-world new-world false))])
    (pretty-printer (last @worlds))))
  