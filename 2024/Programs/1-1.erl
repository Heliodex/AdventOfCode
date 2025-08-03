-module('1-1').
-export([start/0]).

process_data(Content) ->
    Nums = [
        {binary_to_integer(A), binary_to_integer(B)}
     || Line <- binary:split(Content, <<"\n">>, [global]),
        [A, B] <- [binary:split(Line, <<"   ">>, [global])]
    ],
    lists:sum(
        lists:map(
            fun({A, B}) -> abs(A - B) end,
            lists:zip(
                lists:sort(lists:map(fun({A, _}) -> A end, Nums)),
                lists:sort(lists:map(fun({_, B}) -> B end, Nums))
            )
        )
    ).

main() ->
    case file:read_file("../Inputs/1") of
        {ok, Content} ->
            Solution = process_data(Content),
            io:format("Solution: ~p~n", [Solution]);
        {error, Reason} ->
            io:format("Error reading file: ~p", [Reason])
    end.

start() ->
    try
        main()
    catch
        error:Reason:Stack ->
            io:format("Error: ~p~nStack: ~p~n", [Reason, Stack])
    end,
    io:format("~n"),
    init:stop().
