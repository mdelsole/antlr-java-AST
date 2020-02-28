Assumptions:

Scoping:
    - The project description was unclear on what exactly needed to carry over between scopes. We implemented a mix of
    all options to demonstrate functionality, although they might not match pascal exactly. Our implementation is as
    follows:

        -For/while loops: A new scope is created for these loops. Variables declared within loops are not transferred
        back to the parent scope. Parent scope variables are able to be affected by operations within the loop.

        -User-defined functions: A new scope is created for within functions. In functions, parent scope variables can
        NOT be affected by operations within the function. We understood functions to simply be about calculating the
        return value.

        -User-defined procedures: A new scope is created for within procedures. In procedures, parent scope variables
        CAN be affected by operations within the procedure, but only if that variable was passed in as a parameter. We
        chose not to allow all global variables to be affected, since that implementation would basically be the same
        thing as just putting the code in the main program block, which seemed pointless.

Bonus Implemented:
    - Implement parameter passing in procedures/functions. Procedures should be able to declare parameters that can be
    passed when they are called. These variables should be correctly scoped as well.


