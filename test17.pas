program mytests;
VAR
    c, j : real = 13;
    r : real = 6;
    k, l : boolean = true;
    start : real = 17;

procedure double(num1: real; condit: boolean);
VAR
    result : real;
BEGIN
if (condit) then
    num1 := (num1)*2;
else
    num1 := num1;
END;

BEGIN
    writeln('This is the start of test ', start);

    case (13) of
        r : writeln('My name is Ryan');
        c : writeln('My name is Chris');
    else
        writeln('I do not have a name');
    END;

    writeln('C is now: ', c);
    double(c, k);
    writeln('C is now: ', c);

    l := false;

    double(c, l);
    writeln('C is now: ', c);


END;