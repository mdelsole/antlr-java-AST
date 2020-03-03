program mytests;
VAR
    c, j : real = 13;
    r : real = 6;
    k, l : boolean = true;
    e : real = 8;
    start : real = 20;

procedure wileg4(num1: real; condit: boolean);
VAR
    result : real;
BEGIN
if (condit) then
    while (e > 4) do
    BEGIN
        e := e-1;
    END;
    result := num1;
else
    num1 := 0;
END;

BEGIN
    writeln('This is the start of test ', start);

    writeln('C is now: ', c);
    wileg4(c, k);
    writeln('C is now: ', c);

    l := false;

    wileg4(c, l);
    writeln('C is now: ', c);


END;