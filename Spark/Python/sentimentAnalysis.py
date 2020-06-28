# -*- coding: UTF-8 -*-

import sparkConsumerConfig as config
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer
# from sentita import calculate_polarity

# Vader
analysis = SentimentIntensityAnalyzer()
analysis.lexicon.update(config.twitch_emotes)

def get_sentiment_analysis_en(phrase):  
    polarity = analysis.polarity_scores(phrase)         
    if polarity["compound"] >= 0.05:
        if polarity['pos'] - polarity["neu"] > 0.1:
            return 'very_positive'
        elif 0 <= abs(polarity['pos'] - polarity["neu"]) <= 0.6:
            if polarity['neg'] > 0.1:
                return 'ironic'
            else:
                return 'positive_opinion'
        elif polarity['pos'] - polarity["neg"] < 0.2 and polarity['pos'] > 0:
            return 'positive_opinion'
        else:
            return 'neutral_opinion'
    elif polarity["compound"] <= -0.05:
        if polarity['neg'] - polarity["neu"] > 0.1:
            return 'very_negative'
        elif 0 <= abs(polarity['neg'] - polarity["neu"]) <= 0.6:
            if polarity['pos'] > 0.1:
                return 'ironic'
            else:
                return 'negative_opinion'
        elif polarity['neu'] - polarity["neg"] < 0.2 and polarity['neg'] > 0:
            return 'negative_opinion'
        else:
            return 'neutral_opinion'
    else:
        if polarity["pos"] > 0 and polarity["neg"] > 0:
            return "ironic"
        elif polarity['neu'] - polarity["pos"] < 0.4:
            return "positive_opinion"
        elif polarity['neu'] - polarity["neg"] < 0.4:
            return 'negative_opinion'
        else:
            return 'neutral_opinion'

# def get_sentiment_analysis_ita(phrase):   
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