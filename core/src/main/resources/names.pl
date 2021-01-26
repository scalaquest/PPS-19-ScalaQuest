name(ugo) --> [ugo].
name(giuseppe) --> [giuseppe].
name(marco) --> [marco].

name(gift) --> [gift].
name(key) --> [key].
name(door) --> [door].
name(water) --> [water].
name(apple) --> [apple].

adj(X^big(X)) --> [big].
adj(X^red(X)) --> [red].
adj(X^green(X)) --> [green].

tv(X^Y^use(Y,X)) --> [use].
tv(X^Y^eat(Y,X)) --> [eat].
tv(X^Y^take(Y,X)) --> [take].
tv(X^Y^pick_up(Y,X)) --> [pick, up].

v(2/to, Y^X^go(X, Y)) --> [go].
v(3/to, Z^Y^X^give(X,Y,Z) ) --> [give].
v(3/from, Z^Y^X^buy(X,Y,Z) ) --> [buy].
v(3/into, Z^Y^X^insert(X,Y,Z)) --> [insert].
v(3/with, Z^Y^X^hit(X,Y,Z)) --> [hit].
v(3/from, Z^Y^X^hitfrom(X,Y,Z)) --> [hit].