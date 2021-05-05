import os
import numpy as np

def get_score_list_from_file(file_path):
    with open(file_path, 'r') as f:
        return [int(line.rstrip().split(' ')[1]) for line in f]


def get_query_scores_dict(relevance_path):
    # dir_path is the full path to the show directory
    # files is a list of all the files in the show directory
    directories = [(dir_path, files) for (dir_path, _, files) in os.walk(relevance_path) if len(files) > 0]
    query_relevance_scores = {}
    for directory in directories:
        query_name = directory[0].split('/')[-1]
        query_relevance_scores[query_name] = []
        for relevance_file in directory[1]:
            path_to_relevance_score = os.path.join(relevance_path, f"{directory[0]}/{relevance_file}")
            relevance_score_list = get_score_list_from_file(path_to_relevance_score)
            query_relevance_scores[query_name].append(relevance_score_list)
    return query_relevance_scores

def get_average_score_for_query(scores_2d_list):
    data = np.array(scores_2d_list)
    avg = np.average(data, axis=0)
    return np.rint(avg)

def get_dict_of_average_scores(scores_for_queries):
    average_scores = {query : get_average_score_for_query(l) for query, l in scores_for_queries.items()}
    return average_scores

def get_nDCG_for_query(avg_scores):
    dcg = 0.0
    idc = 0.0
    for i in range(len(avg_scores)):
        rel_i = avg_scores[i]
        two_pow_rel_i = (2**rel_i) - 1
        dcg_p = rel_i/np.log2(i+2)
        idc_p = two_pow_rel_i/np.log2(i+2)
        dcg += dcg_p
        idc += idc_p 
    return dcg/idc

def get_all_nDCGs(avg_scores_dict):
    return [(query, get_nDCG_for_query(avg)) for query, avg in avg_scores_dict.items()]

def print_formated(nDCG_tuples):
    for (query, nDCG) in nDCG_tuples:
        print(f"The nDCG of query: {query} is: {nDCG}")

def main():
    current_path = os.path.realpath(__file__)
    relevance_path = os.path.join(os.path.dirname(current_path), 'relevance_scores')
    scores_for_queries = get_query_scores_dict(relevance_path)
    avg_scores_dict = get_dict_of_average_scores(scores_for_queries)
    nDCG_tuples = get_all_nDCGs(avg_scores_dict)
    print_formated(nDCG_tuples)

if __name__ == '__main__':
    main()