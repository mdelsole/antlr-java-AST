program test10;
VAR
    r : real = 13;
    spacer: real = 11111111111111111111111111111111111111111;
    b : boolean = true;
BEGIN
    if b then
        writeln(r);
    else
        writeln(b);

    writeln(spacer);

    if not b then
        writeln(r);
    else
        writeln(b);
END;

(*OUTPUT
13.0
11111111111111111111111111111111111111111
true
*)