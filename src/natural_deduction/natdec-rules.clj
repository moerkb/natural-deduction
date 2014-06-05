(ns natural-deduction.core)

; AND
(def and-e1-rule
  '{:args [$and $a]
    :forms [[$and ($a ∧ $b)]]
    :foreward $a})

(def and-e2-rule
  '{:args [$and $b]
    :forms [[$and ($a ∧ $b)]]
    :foreward $b})

(def and-i1-rule
  '{:args [$a $b $and]
    :forms [[$and ($a ∧ $b)]]
    :foreward $and})

(def and-i2-rule
  '{:args [$a $b $and]
    :forms [[$and ($b ∧ $a)]]
    :foreward $and})

(def and-i1-backward-rule
  '{:args [$a $and]
    :forms [[$and ($a ∧ $b)]]
    :backward $a})

(def and-i2-backward-rule
  '{:args [$b $and]
    :forms [[$and ($a ∧ $b)]]
    :backward $b})

; OR
(def or-e-rule
  '{:args [$or $proofs $X]
    :forms [[$or ($a ∨ $b)]
            [$proofs (($a ⊢ $X)($b ⊢ $X))]]
    :foreward $proofs})

(def or-i1-rule
  '{:args [$a $or]
    :forms [[$or ($a ∨ $b)]]
    :foreward $or
    :backward $a})

(def or-i2-rule
  '{:args [$b $or]
    :forms [[$or ($a ∨ $b)]]
    :foreward $or
    :backward $b})

; IMPL
(def impl-e-rule
  '{:args [$a $impl $b]
    :forms [[$impl ($a → $b)]]
    :foreward $b})

(def impl-i-rule
  '{:args [$proof $impl]
    :forms [[$proof ($a ⊢ $b)]
            [$impl ($a → $b)]]
    :backward $proof})

; NOT
(def not-e-rule
  '{:args [$and $contradiction]
    :forms [[$and ($a ∧ (¬ $a))]
            [$contradiction ⊥]]
    :foreward $contradiction
    :backward $and})

(def not-i-rule
  '{:args [$proof $res]
    :forms [[$proof ($a ⊢ ⊥)]
            [$res (¬ $a)]]
    :backward $proof})

; RAA, ⊥
(def efq-rule
  '{:args [$contradiction $a]
    :forms [[$contradiction ⊥]]
    :foreward $a
    :backward $contradiction})

(def raa-rule
  '{:args [$proof $a]
    :forms [[$proof ((¬ $a) ⊢ ⊥)]]
    :backward $proof})

; ALL
(def all-i-rule
  '{:args [$proof $all]
    :forms [[$proof ((var $i) ⊢ (substitution $all $i))]
            [$all (predicate-formula ∀ $x $predicate-formula)]]
    :backward $proof})

(def all-e-rule
  '{:args [$all $var $substitute]
    :forms [[$all (predicate-formula ∀ $x $predicate-formula)]
            [$var (var $t)]
            [$substitute (substitution $all $t)]]
    :foreward $substitute})

; EXISTS
(def exists-i-rule
  '{:args [$var $substitute $exists]
    :forms [[$var (var $t)]
            [$substitute (substitution $exists $t)]
            [$exists (predicate-formula ∃ $x $predicate-formula)]]
    :backward $substitute})

(def exists-e-rule
  '{:args [$exists $proof $X]
    :forms [[$exists (predicate-formula ∃ $x $predicate-formula)]
            [$proof ((var $x0) (substitution $exists $x0) ⊢ $X)]]
    :foreward $proof})