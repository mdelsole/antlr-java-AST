program mytest;
VAR
    c, j : real = 13;
    r : real = 6;
    q : real = 2;
    k, l : boolean = true;
    start : real = 19;

function forr(num1, num2: real; condit: boolean): real;
VAR
    result : real;

BEGIN
    if (condit) then
        for x := 0 to 8 do
        BEGIN
            if (x >5) then
                result := num1+x;
                break;
            else
                writeln(x);
        END;
    else
        result := num2
    forr := result;
END;



BEGIN
    writeln('This is the start of test ', start);


    writeln('Q is now', q);
    q:= forr(r,j,k);
    writeln('Q is now', q);

END;