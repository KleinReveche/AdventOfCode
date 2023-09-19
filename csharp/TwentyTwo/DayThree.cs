using static AdventOfCode.Application;

namespace AdventOfCode.TwentyTwo;

internal static class DayThree
{
    private record RucksackItems(string FirstHalf, string SecondHalf, string Combined);
    internal static void Solve()
    {
        int sum = 0, groupedSum = 0;
        var input = ReadInput("day03");
        List<RucksackItems> rucksackItems = new();
        List<List<RucksackItems>> groupedRucksackItems = new();
        ReadData(input, rucksackItems);

        sum += rucksackItems.Sum(item => CheckForCommonLetter(item.FirstHalf, item.SecondHalf));

        for (var i = 0; i < rucksackItems.Count; i++)
        {
            if ((i + 1) % 3 != 0) continue;
            List<RucksackItems> grouped = new()
            {
                rucksackItems[i - 2],
                rucksackItems[i - 1],
                rucksackItems[i],
            };
            groupedRucksackItems.Add(grouped);
        }

        groupedSum += groupedRucksackItems.Sum(groupedItems => CheckForCommonLetter(groupedItems[0].Combined, groupedItems[1].Combined, groupedItems[2].Combined));

        Console.WriteLine(" --- 2022 Day 3: Rucksack Reorganization ---\n");
        Console.WriteLine($"   The sum of all item type priorities of all the Elves' rucksack are: {sum}");
        Console.WriteLine($"   Also, all item type priorities of three grouped Elves' rucksack are: {groupedSum}\n");
    }

    private static void ReadData(string input, ICollection<RucksackItems> rucksackItems)
    {
        var items = input.Trim().Split('\n');

        foreach (var item in items)
        {
            var middleLineIndex = item.Length / 2;
            var firstHalf = item[..middleLineIndex];
            var secondHalf = item[middleLineIndex..];
            rucksackItems.Add(new RucksackItems(firstHalf, secondHalf, item));
        }
    }

    private static int CheckForCommonLetter(params string[] strings)
    {
        var charArrays = strings.Select(item => item.ToCharArray().ToHashSet()).ToArray();
        var commonLetter = charArrays.Aggregate((acc, set) => (HashSet<char>)acc.Intersect(set));

        return commonLetter.First() switch
        {
            >= 'a' and <= 'z' => commonLetter.First() - 'a' + 1,
            >= 'A' and <= 'Z' => commonLetter.First() - 'A' + 27,
            _ => throw new ArgumentException($"Invalid character: {commonLetter.First()}")
        };
    }
}