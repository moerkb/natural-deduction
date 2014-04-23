(ns natural-deduction.core)

; and
(def and-i {:name "∧i"
            :pre '[$1 $2]
            :post '(and $1 $2)
            :dir :both})

(def and-e1 {:name "∧e1"
             :pre '[(and $1 $2)]
             :post '$1
             :dir :forward})

(def and-e2 {:name "∧e2"
             :pre '[(and $1 $2)]
             :post '$2
             :dir :forward})

; or
(def or-i1 {:name "∨i1"
            :pre '[$1]
            :post '(or $1 $2)
            :dir :both})

(def or-i2 {:name "∨i2"
            :pre '[$2]
            :post '(or $1 $2)
            :dir :both})

(def or-e {:name "∨e"
           :pre '[(or $1 $2) [$1 $3] [$2 $3]]
           :post '$3
           :dir :forward})

; implication
(def impl-i {:name "→i"
             :pre '[[$1 $2]]
             :post '(impl $1 $2)
             :dir :backward})

(def impl-e {:name "→e, MP"
             :pre '[$1 (impl $1 $2)]
             :post '$2
             :dir :forward})

; negation
(def not-i {:name "¬i"
            :pre '[[$1 :contradiction]]
            :post '(not $1)
            :dir :backward})

(def not-e {:name "¬e"
            :pre '[$1 (not $1)]
            :post :contradiction
            :dir :backward})

; reductio ad absurdum, contradiction
(def raa {:name "RAA"
          :pre '[[(not $1) :contradiction]]
          :post '$1
          :dir :backward})

(def efq {:name "⊥e, EFQ"
          :pre '[:contradiction]
          :post '$1
          :dir :both})