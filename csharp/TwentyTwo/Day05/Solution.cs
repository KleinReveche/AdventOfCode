using System.Text;

namespace AdventOfCode.TwentyTwo.Day05;

[Problem(Year.TwentyTwo, "Day05", "Supply Stacks")]
public class Solution(string input) : ISolution
{
    public object PartOne()
    {
        var crates = Solve(true);
        var topCrateLetters = new string(crates.Select(x => x.Value.Last()).ToArray());

        return $"After the Rearrangement procedure was done, the top crates are: {topCrateLetters}";
    }

    public object PartTwo()
    {
        var crates = Solve(false);
        var topCrateLetters = new string(crates.Select(x => x.Value.Last()).ToArray());

        return $"Due to the upgrade of the crane to the new CrateMover 9001, the new top crates are: {topCrateLetters}";
    }

    private Dictionary<int, Stack<char>> Solve(bool isUpgraded)
    {
        ReadDayFiveData(out var supplyCrateMoves, out var crateTableStack);
        var crateStack = RotateDictionary(crateTableStack);
        return CrateMover9000(crateStack, supplyCrateMoves, isUpgraded);
    }

    private void ReadDayFiveData(out List<SupplyCrateMoves> supplyCrateMoves,
        out Dictionary<int, Stack<char>> crateTableStack)
    {
        List<SupplyCrateMoves> separatedItems = [];
        Dictionary<int, Stack<char>> crateTableStackTemp = new();
        var currentCrateIndex = 0;
        StringReader inputLines = new(input);

        while (true)
        {
            var line = inputLines.ReadLine();

            if (line != null)
            {
                if (line.StartsWith("move"))
                {
                    var lineSplit = line.Trim().Split(" from ");
                    var crateCount = int.Parse(lineSplit[0].Replace("move ", string.Empty));
                    var origDest = lineSplit[1].Split(" to ");
                    separatedItems.Add(new SupplyCrateMoves(crateCount, int.Parse(origDest[0]) - 1,
                        int.Parse(origDest[1]) - 1));
                }
                else
                {
                    if (!line.Contains('[')) continue;
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

                        if (t != ' ' && t != '[' && t != ']')
                            crate.Push(t);
                    }

                    crateTableStackTemp.Add(currentCrateIndex++, crate);
                }
            }
            else
            {
                break;
            }
        }

        crateTableStack = crateTableStackTemp;
        supplyCrateMoves = separatedItems;
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

            if (!isUpgraded)
            {
                var cratesToMoveTemp = new Stack<char>();
                while (cratesToMove.Count > 0) cratesToMoveTemp.Push(cratesToMove.Pop());
                cratesToMove = cratesToMoveTemp;
            }

            while (cratesToMove.Count > 0) destStack.Push(cratesToMove.Pop());

            // update the stacks
            crateStack[origin] = originStack;
            crateStack[dest] = destStack;
        }

        for (var index = 0; index < crateStack.Count; index++)
        {
            var stack = crateStack[index];
            foreach (var letter in stack) Console.Write(letter);

            Console.WriteLine();
        }

        return crateStack;
    }

    private static Dictionary<int, Stack<char>> RotateDictionary(Dictionary<int, Stack<char>> crateTableStack)
    {
        Dictionary<int, Stack<char>> newTableStack = new();
        var maxSize = crateTableStack.Max(x => x.Value.Count);
        var maxKey = crateTableStack.Max(x => x.Key) + 1;

        for (var i = 0; i < maxSize; i++)
        {
            Stack<char> newStack = new();

            foreach (var (_, value) in crateTableStack)
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
}