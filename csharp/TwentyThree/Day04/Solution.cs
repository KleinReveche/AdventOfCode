
using System.Text.RegularExpressions;

namespace AdventOfCode.TwentyThree.Day04;

[Problem(Year.TwentyThree, "Day04", "Scratchcards")]
public partial class Solution(string input) : ISolution
{
    private readonly Card[] _cards = input.Split("\n", StringSplitOptions.RemoveEmptyEntries).Select(ParseCard).ToArray();
    
    public object PartOne()
    {
        var points = _cards.Select(card => (int)Math.Pow(2, card.Wins - 1)).Sum();
        return $"The Elf's total number of points is {points}.";
    }

    public object PartTwo()
    {
        var cardCounts = _cards.Select(_ => 1).ToArray();
        
        for (var i = 0; i < _cards.Length; i++) 
        {
            var (card, count) = (_cards[i], cardCounts[i]);
            for (var j = 0; j < card.Wins; j++) 
            {
                cardCounts[i + j + 1] += count;
            }
        }
        
        return $"Total scratchcards do you end up with is {cardCounts.Sum()}.";
    }

    private static Card ParseCard(string line)
    {
        var parts = line.Split(':', '|');
        var scratchCardNumbers = DigitRegex().Matches(parts[1]).Select(m => m.Value);
        var winningNumbers = DigitRegex().Matches(parts[2]).Select(m => m.Value);
        return new Card(scratchCardNumbers.Intersect(winningNumbers).Count());
    }

    private record Card(int Wins);

    [GeneratedRegex(@"\d+")]
    private static partial Regex DigitRegex();
}