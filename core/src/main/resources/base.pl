:- load_library('alice.tuprolog.lib.DCGLibrary').

/**
 * Glossary:
 *
 * s   - present simple phrase (unused)
 * imp - imperative phrase
 * vp  - verbal phrase
 * np  - nominal phrase
 * pp  - prepositional phrase
 */

% Present simple phrase
s(S) --> np(Arg), vp(Arg^S).
% Imperative phrase
imp(S) --> vp(you^S).
% Intransitive verb
vp(Sem) --> vp(1/Pform, Sem).
% Transitive verb
vp(Sem) --> vp(2/Pform, Sem).
% Ditransitive verb
vp(Sem) --> vp(3/Pform, Sem).

% Preposition not followed by substantive
ppN(Form) --> p(Form, _).
% Allow swapping preposition and np for phrasal verbs recognition
pp(2/Form, Sem) --> p(Form, X^Sem), np(Sem).
pp(2/Form, Sem) --> np(Sem), p(Form,X^Sem).
% In ditransitive sentences preposition comes before the name
pp(3/Form, Sem) --> p(Form,X^Sem), np(Sem).

% Intransitive verb followed by preposition.
vp(1/Pform, Sem) -->
  v(1/Pform, Sem),
  ppN(Pform).

% Transitive verb followed by a complement.
vp(2/Pform, Sem) -->
	v(2/Pform,Y^Sem),
	pp(2/Pform,Y).

% Ditransitive verb followed by two complements.
vp(3/Pform, Sem) -->
	v(3/Pform,Z^Y^Sem),
	np(Y),
	pp(3/Pform,Z).

% Nominal phrase
np(X) --> det, substantive(X).
np(X) --> substantive(X).

% A substantive might be preceded by many adjectives.
substantive(X) --> adj(Y^X), substantive(Y).
substantive(X) --> [X], { name(X) }.

% Adjectives must be declared before their usage.
adj(Y^decorated(X,Y)) -->
  [X],
  { adjective(X) }.

% Allow no articles.
% det --> []. // TODO: remove
det --> [a].
det --> [an].
det --> [the].

% Special symbol for empty preposition is {}
p({}, _) --> [].
p(X, _) -->
	[X].

% Verb creation helpers
verb(X, Y, {}) :- verb(X, Y).

v(1/Prep, X^sentence(Verb/Prep, X)) -->
  [Verb],
  { verb(1, Verb, Prep) }.
v(2/Prep, Y^X^sentence(Verb/Prep, X, Y)) -->
  [Verb],
  { verb(2, Verb, Prep) }.
v(3/Prep, Z^Y^X^sentence(Verb/Prep, X, Y, Z)) -->
  [Verb],
  { verb(3, Verb, Prep) }.