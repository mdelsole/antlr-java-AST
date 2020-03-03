program mytest;
VAR
    c, j : real = 13;
    r : real = 6;
    q : real = 2;
    k, l : boolean = true;
    start : real = 18;

function caser(num1, num2: real; condit: boolean): real;
VAR
    result : real;

BEGIN
    if (condit) then
        case (num1) of
            6 : writeln('YOU FOUND ME');
        else
            writeln('Move along.');
        END;
        result := 6;
    else
        result := num2;
    writeln('Yo');

    caser := result;
END;



BEGIN
    writeln('This is the start of test ', start);


    writeln('Q is now ', q);
    q := caser(r,j,k);
    writeln('Q is now ', q);

END;