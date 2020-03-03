program test3;
VAR
    o : real = 13;
    p : real = 13.5;
    re : real = o + p;
    ree : real = p * o;
BEGIN
    writeln(re);
    writeln(ree);
END;

(*OUTPUT
26.5
175.5
*)