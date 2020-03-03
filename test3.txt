program mytests;
VAR
    k : real = 8;
    x : real = 6;

BEGIN

    for x := 0 to 8 do
    BEGIN
        if (x >5) then
            break;
        else
            writeln(x);
    END;

    while (k > 4) do
    BEGIN
        k := k-1;
        writeln('K is now: ', k);
    END;

END;