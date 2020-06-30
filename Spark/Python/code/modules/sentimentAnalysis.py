# -*- coding: UTF-8 -*-

# from sentita import calculate_polarity
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer

# set your custom emote value to use for sentiment analyzer. Keep in mind to set a balanced values for each emote.
twitch_emotes = {
    '<3': 0.4,
    '4head': 1,
    'babyrage': -0.7,
    'biblethump': -0.7,
    'blessrng': 0.3,
    'bloodtrail': 0.7,
    'coolstorybob': -1,
    'residentsleeper': -1,
    'kappa': 0.3,
    'lul': 0.1,
    'pogchamp': 1.5,
    'heyguys': 1,
    'wutface': -1.5,
    'kreygasm': 1,
    'seemsgood': 0.7,
    'kappapride': 0.7,
    'feelsgoodman': 1,
    'notlikethis': -1
}

#Vader
analyzer = SentimentIntensityAnalyzer()
analyzer.lexicon.update(twitch_emotes)

def get_sentiment_analyzer_en(phrase): 
    polarity = analyzer.polarity_scores(phrase)         
    if polarity["compound"] >= 0.05:
        if polarity['pos'] - polarity["neu"] > 0.1:
            return 'very_positive'
        elif 0 <= abs(polarity['pos'] - polarity["neu"]) <= 0.6:
            if polarity['neg'] > 0.05:
                return 'ironic'
        return 'positive_opinion'
    elif polarity["compound"] <= -0.05:
        if polarity['neg'] - polarity["neu"] > 0.1:
            return 'very_negative'
        elif 0 <= abs(polarity['neg'] - polarity["neu"]) <= 0.6:
            if polarity['pos'] > 0.05:
                return 'ironic'
        return 'negative_opinion'
    else:
        if polarity["pos"] > 0 and polarity["neg"] > 0:
            return "ironic"
        elif polarity['neu'] - polarity["pos"] < 0.4:
            return "positive_opinion"
        elif polarity['neu'] - polarity["neg"] < 0.4:
            return 'negative_opinion'
        return 'neutral_opinion'

# def get_sentiment_analyzer_ita(phrase):   
#     data = [phrase]
#     results, polarities = calculate_polarity(data)   
#     if abs(polarities[0] - polarities[1]) < 2:
#         return "neutral_opinion"
#     elif polarities[0] - polarities[1] >= 4:
#         return "very_positive" 
#     elif polarities[0] - polarities[1] >= 2:
#         return "positive_opinion" 
#     elif polarities[1] - polarities[0] >= 4:
#         return "very_negative" 
#     elif polarities[1] - polarities[0] >= 2:
#         return "negative_opinion"             