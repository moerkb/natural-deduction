(ns natural-deduction.core)

; and
(def and-i {:name "∧i"
            :pre-top '[$1 $2]
            :subs '[(and $1 $2)]})

(def and-e1 {:name "∧e1"
             :pre-top '[(and $1 $2)]
             :subs '[$1]})

(def and-e2 {:name "∧e2"
             :pre-top '[(and $1 $2)]
             :subs '[$2]})

; or
(def or-i1 {:name "∨i1"
            :pre-top '[$1]
            :subs '[(or $1 $2)]})

(def or-i2 {:name "∨i2"
            :pre-top '[$2]
            :subs '[(or $1 $2)]})

(def or-e {:name "∨e"
           :pre-top '[(or $1 $2)]
           :pre-bot '[$3]
           :subs '[[$1 $2] [$1 $3]]})

; implication
(def impl-i {:name "→i"
             :pre-top '[$1]
             :pre-bot '[(impl $1 $2)]
             :subs '[[$1 $2]]})

(def impl-e {:name "→e, MP"
             :pre-top '[$1 (impl $1 $2)]
             :subs '[$2]})

; negation
(def not-i {:name "¬i"
            :pre-top '[$1]
            :subs '[[$1 :contradiction] (not $1)]})

(def not-e {:name "¬e"
            :pre-top '[$1 (not $1)]
            :subs '[:contradiction]})

; reduction ad absurdum, contradiction
(def raa {:name "RAA"
          :pre-bot '[$1]
          :subs '[[(not $1) :contradiction]]})

(def efq {:name "⊥e, EFQ"
          :pre-top '[:contradiction]
          :subs '[$1]})