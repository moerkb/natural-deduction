(ns natural-deduction.core)

(def worlds (atom []))
(def counter (atom 0))

(defn build-world
  "Get a nested vector as world and transform it.

   Every elemtent becomes a hash-set with body, a hash number and a rule:
   :body is the old element
   :hash is a number to ident this element
   :rule is the rule that returns this entry (in this case rule is :premise before infer)

   To get a proof you need an infer \"⊢\".
   An infer gets the body :todo

   E.g. [a ⊢ b] => [#{:body a, :hash 1, :rule :premise} #{:body :todo, :hash 2, :rule nil} #{:body b, :hash 2, :rule nil}]"
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
  "Get a transformed world element and return a pretty String of this element.
   A body with :todo becomes a \"...\"

   E.g.  #{:body a, :hash 1; :rule :premise} => \"a (#1 premise)\""
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
  "Gets a transformed world and print it on the stdout.
   Returns nil."
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
  "Go one step backward in the history.
   Go no step backward if the initial state is arrived."
  []
  (do
    (when (second @worlds) (reset! worlds (vec (drop-last @worlds))))
    (pretty-printer (last @worlds))))

(defn set-world!
  "Gets an nested vector and initialize the world with it.
   It is importand to give a untransformed vector!!!
   The transformation (build-world) works internal."
  [new-world]
  (do
    (reset! counter 0)
    (reset! worlds [(vec (build-world new-world))])
    (pretty-printer (last @worlds))))
  