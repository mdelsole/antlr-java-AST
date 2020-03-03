program test11;
VAR
    r : real = 13;
    c: real = 11111111111111111111111111111111111111111;
    b : boolean = true;
BEGIN
    readln(c);
    case c of
     1 : writeln(1);
     2 : writeln(2);
     3 : writeln(3);
    else
      writeln(InvalidCase);
END;

(*OUTPUT
Depending on what case you choose with the readln line.
*)