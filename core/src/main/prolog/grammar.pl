% go n
% go north
% go(you, north)

% take the apple
% take an apple
% take apple
% pick up apple
% take(you, apple)

% use key on the door
% use the key on door
% use(you, key, door)
% % insert the key on the door
% % insert(you, key, door)

:- load_library('alice.tuprolog.lib.DCGLibrary').

s(S) --> np(Arg), vp(Arg^S).
i(S) --> vp(you^S).

vp(VP) --> iv(VP).
vp(VP) --> tv(NP^VP), np(NP).
vp(VP) --> vp(2/Pform, VP).
vp(VP) --> vp(3/Pform, VP).
vp(VP) --> vp(4/Pform, VP).

pp(Form, Sem) --> p(Form,X^Sem), np(Sem).

p(to, _) --> [to].
p(with, _) --> [with].
p(from, _) --> [from].
p(in, _) --> [in].
p(into, _) --> [into].
p(between, _) --> [between].

vp(2/Pform, Sem) -->
	v(2/Pform,Y^Sem),
	pp(Pform,Y).

vp(3/Pform, Sem) -->
	v(3/Pform,Z^Y^Sem),
	np(Y),
	pp(Pform,Z).

%vp(4/Pform, Sem) -->
%	v(4/Pform,Z^Y^X^Sem),
%	np(X),
%	np(Y),
%	pp(Pform,Z).

tv(X^Y^use(Y,X)) --> [use].
tv(X^Y^take(Y,X)) --> [take].

%v(2/to, Y^X^go(X, Y)) --> [go].
v(3/to, Z^Y^X^give(X,Y,Z) ) --> [give].
v(3/from, Z^Y^X^buy(X,Y,Z) ) --> [buy].
v(3/into, Z^Y^X^insert(X,Y,Z)) --> [insert].
v(3/with, Z^Y^X^hit(X,Y,Z)) --> [hit].
v(3/from, Z^Y^X^hitfrom(X,Y,Z)) --> [hit].

det --> [].
det --> [a].
det --> [an].
det --> [the].

np(X) --> det, name(X).

name(ugo) --> [ugo].
name(giuseppe) --> [giuseppe].
name(marco) --> [marco].

name(gift) --> [gift].
name(key) --> [key].
name(door) --> [door].
name(water) --> [water].
name(apple) --> [apple].
