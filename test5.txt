program mytests;
VAR
    i : boolean;
    j, m : boolean = true;
    y : boolean = j and (8 > 7 and 7 > 8);
    k : real = 8;
    z : real = k+cos(0.0/1.0);

function max(num1, num2: real): real;
VAR
   result : real;

BEGIN
   if (num1 > num2) then
      result := num1;
      writeln('yo');
   else
      result := num2;
   max := result;
END;


BEGIN
    writeln('K is: ', k);
    writeln('J is: ', j);


    if (j and i) then
        k := 1;
    else if (true and true) then
        k := 6;
        writeln('K is now: ', k);
    else if (true and false) then
        k := 3;
    else
        k := 4;
    k := max(z,7);

    writeln('K is now: ', k);

END;