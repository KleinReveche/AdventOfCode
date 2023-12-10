using System.Text.RegularExpressions;

namespace AdventOfCode.TwentyThree.Day03;

[Problem(Year.TwentyThree, "Day03", "Gear Ratios")]
public partial class Solution : ISolution
{
    private readonly string[] _data;
    private readonly Dictionary<Symbol, HashSet<int>> _numbers;
    private readonly Symbol[] _symbols;

    public Solution(string input)
    {
        _data = input.Trim().Split('\n', StringSplitOptions.RemoveEmptyEntries);
        _symbols = FindSymbols();
        _numbers = GetNumbers();
    }

    public object PartOne()
    {
        var sum = _numbers.Sum(kvp => kvp.Value.Sum());
        return $"The sum of all of the part numbers in the engine schematic is {sum}.";
    }

    public object PartTwo()
    {
        var sum = _numbers.Sum(it =>
        {
            if (it.Key.Sign == '*' && it.Value.Count == 2) return it.Value.Aggregate((a, b) => a * b);
            return 0;
        });
        return $"the sum of all of the gear ratios in your engine schematic is {sum}.";
    }

    private Dictionary<Symbol, HashSet<int>> GetNumbers()
    {
        var dict = new Dictionary<Symbol, HashSet<int>>();

        foreach (var symbol in _symbols)
        {
            var numbers = new HashSet<int>();
            for (var y = symbol.Y - 1; y <= symbol.Y + 1; y++)
            {
                for (var x = symbol.X - 1; x <= symbol.X + 1; x++)
                {
                    if (x == symbol.X && y == symbol.Y) continue;
                    if (!int.TryParse(_data[y][x].ToString(), out _)) continue;

                    var left = x - 1;
                    while (left >= 0 && char.IsDigit(_data[y][left])) left--;

                    var right = x + 1;
                    while (right < _data[y].Length && char.IsDigit(_data[y][right])) right++;

                    var number = int.Parse(_data[y].Substring(left + 1, right - left - 1));

                    numbers.Add(number);
                }
            }
            dict.Add(symbol, numbers);
        }

        return dict;
    }

    private Symbol[] FindSymbols()
    {
        var symbols = new List<Symbol>();
        var regex = SymbolRegex();

        for (var y = 0; y < _data.Length; y++)
        for (var x = 0; x < _data[y].Length; x++)
        {
            var symbol = regex.Match(_data[y][x].ToString());
            if (symbol.Success) symbols.Add(new Symbol(symbol.Value[0], x, y));
        }

        return symbols.ToArray();
    }

    [GeneratedRegex(@"[^.0-9]")]
    private static partial Regex SymbolRegex();

    private record Symbol(char Sign, int X, int Y);
}