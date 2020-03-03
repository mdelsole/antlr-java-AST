program test4;
VAR
    r : boolean = false;
    b : boolean = true;
    c : boolean = r or b;
    d : boolean = r and b;
    e : boolean = not r;
BEGIN
    writeln(c);
    writeln(d);
    writeln(e);
END;

(*OUTPUT
true
false
true
*)