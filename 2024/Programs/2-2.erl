-module('2-2').
-export([start/0]).

% someday I'll look back on this code and understand it better

group([]) ->
    [];
group([_]) ->
    [];
group([A, B | Rest]) ->
    [A - B | group([B] ++ Rest)].

delete_at([], _) ->
    [];
delete_at([_ | Rest], 0) ->
    Rest;
delete_at([H | Rest], Index) when Index > 0 ->
    [H | delete_at(Rest, Index - 1)].

remove_each_index(Array) ->
    lists:map(
        fun(Index) ->
            delete_at(Array, Index)
        end,
        lists:seq(0, length(Array) - 1)
    ).

is_safe(Nums) ->
    Grouped = group(Nums),
    lists:all(fun(X) -> 0 < X andalso X < 4 end, Grouped) orelse
        lists:all(fun(X) -> -4 < X andalso X < 0 end, Grouped).

process_data(Content) ->
    Nums = [
        lists:map(fun(X) -> binary_to_integer(X) end, Col)
     || Line <- binary:split(Content, <<"\n">>, [global]),
        Col <- [binary:split(Line, <<" ">>, [global])]
    ],
    lists:sum(
        lists:map(
            fun(X) ->
                case lists:any(fun is_safe/1, remove_each_index(X)) of
                    false -> 0;
                    true -> 1
                end
            end,
            Nums
        )
    ).

main() ->
    case file:read_file("../Inputs/2") of
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
