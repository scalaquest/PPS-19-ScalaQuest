:- load_library('alice.tuprolog.lib.DCGLibrary').

s(S) --> np(Arg), vp(Arg^S).
i(S) --> vp(you^S).

vp(VP) --> iv(VP).
vp(VP) --> tv(NP^VP), np(NP).
vp(VP) --> vp(3/Pform, VP).
vp(VP) --> vp(3/Pform, VP).

vp(2/Pform, Sem) -->
	v(2/Pform,Y^Sem),
	pp(Pform,Y).

vp(3/Pform, Sem) -->
	v(3/Pform,Z^Y^Sem),
	np(Y),
	pp(Pform,Z).

np(X) --> det, name(X).

name(Y) --> adj(X^Y), name(X).

det --> [].
det --> [a].
det --> [an].
det --> [the].

pp(Form, Sem) --> p(Form,X^Sem), np(Sem).

p(to, _) --> [to].
p(with, _) --> [with].
p(from, _) --> [from].
p(in, _) --> [in].
p(into, _) --> [into].
p(between, _) --> [between].

