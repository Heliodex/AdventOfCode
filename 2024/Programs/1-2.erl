-module('1-2').
-export([start/0]).

count(Value, List) ->
    lists:foldl(
        fun(X, Acc) ->
            if
                X == Value -> Acc + 1;
                true -> Acc
            end
        end,
        0,
        List
    ).

process_data(Content) ->
    Nums = [
        {binary_to_integer(A), binary_to_integer(B)}
     || Line <- binary:split(Content, <<"\n">>, [global]),
        [A, B] <- [binary:split(Line, <<"   ">>, [global])]
    ],
    Anums = lists:sort(lists:map(fun({A, _}) -> A end, Nums)),
    Bnums = lists:sort(lists:map(fun({_, B}) -> B end, Nums)),
    lists:sum(
        lists:map(
            fun(X) -> X * count(X, Bnums) end,
            Anums
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
