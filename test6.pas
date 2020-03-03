program test6;
VAR
    c : real = 13;
    d : real = 22;
    r : real = 6;
    m : real = 8;
    v : real = r*c/(d+m);
BEGIN
    writeln(v);
END;

(*OUTPUT
2.6
*)