using System.Text;
using System.Text.RegularExpressions;

namespace AdventOfCode.TwentyTwo.Day05;

[Problem(Year.TwentyTwo, "Day05", "Supply Stacks")]
public partial class Solution(string input) : ISolution
{
    public object PartOne()
    {
        var crates = Solve(false);
        var topCrateLetters = new string(crates.Select(x => x.Value.First()).ToArray());

        return $"After the Rearrangement procedure was done, the top crates are: {topCrateLetters}";
    }

    public object PartTwo()
    {
        var crates = Solve(true);
        var topCrateLetters = new string(crates.Select(x => x.Value.First()).ToArray());

        return $"Due to the upgrade of the crane to the new CrateMover 9001, the new top crates are: {topCrateLetters}";
    }

    private Dictionary<int, Stack<char>> Solve(bool isUpgraded)
    {
        var (crateTableStack, supplyCrateMoves) = ReadDayFiveData();
        var crateStack = RotateDictionary(crateTableStack); 
        return CrateMover9000(crateStack, supplyCrateMoves, isUpgraded);
    }

    private (Dictionary<int, Stack<char>> crateTableStack, List<SupplyCrateMoves> supplyCrateMoves) ReadDayFiveData()
    {
        List<SupplyCrateMoves> supplyCrateMoves = [];
        Dictionary<int, Stack<char>> crateTableStack = new();
        var currentCrateIndex = 0;
        
        var lines = input.Split("\n", StringSplitOptions.RemoveEmptyEntries);
        var moveRegex = MoveListRegex();

        foreach (var line in lines)
        {
            if (line.StartsWith(" 1")) continue;
            var match = moveRegex.Match(line);

            if (match.Success)
            {
                var crateCount = int.Parse(match.Groups[1].Value);
                var origin = int.Parse(match.Groups[2].Value) - 1;
                var dest = int.Parse(match.Groups[3].Value) - 1;
                
                supplyCrateMoves.Add(new SupplyCrateMoves(crateCount, origin, dest));
                continue;
            }
            
            var crate = new Stack<char>();
            var crateTemp = new StringBuilder();

            for (var i = 0; i <= line.Length - 1; i++)
            {
                var t = line[i];
                crateTemp.Append(t);

                if (crateTemp.Length == 4 || i == line.Length - 1)
                {
                    if (crateTemp[1] == ' ') crate.Push(' ');
                    crateTemp.Clear();
                }

                if (t != ' ' && t != '[' && t != ']') crate.Push(t);
            }

            crateTableStack.Add(currentCrateIndex++, crate);
        }
        
        return (crateTableStack, supplyCrateMoves);
    }

    private static Dictionary<int, Stack<char>> CrateMover9000(Dictionary<int, Stack<char>> crateStack,
        List<SupplyCrateMoves> movesList, bool isUpgraded)
    {
        foreach (var (n, origin, dest) in movesList)
        {
            // move the last n crates from the origin stack to the destination stack
            var originStack = crateStack[origin];
            var destStack = crateStack[dest];
            var cratesToMove = new Stack<char>(); 

            for (var i = 0; i < n; i++) cratesToMove.Push(originStack.Pop());

            if (!isUpgraded) cratesToMove = new Stack<char>(cratesToMove);
            foreach (var crate in cratesToMove)
            {
                destStack.Push(crate);
            }
            
            crateStack[origin] = originStack;
            crateStack[dest] = destStack;
        }

        return crateStack;
    }

    private static Dictionary<int, Stack<char>> RotateDictionary(Dictionary<int, Stack<char>> crateTableStack)
    {
        Dictionary<int, Stack<char>> newTableStack = new();
        var maxSize = crateTableStack.Max(x => x.Value.Count);
        var maxKey = crateTableStack.Max(x => x.Key) + 1;

        for (var i = maxSize - 1; i >= 0; i--)
        {
            Stack<char> newStack = new();

            foreach (var (_, value) in crateTableStack.Reverse())
            {
                if (value.Count <= i) continue;
                var element = value.ElementAt(i);
                if (element != ' ') newStack.Push(element);
            }
            
            newTableStack.Add(maxKey - i, newStack);
        }

        return newTableStack;
    }

    private record SupplyCrateMoves(int CrateCount, int Origin, int Dest);

    [GeneratedRegex(@"move (\d+) from (\d+) to (\d+)")]
    private static partial Regex MoveListRegex();
}