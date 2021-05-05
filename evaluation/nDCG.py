import os

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


def main():
    current_path = os.path.realpath(__file__)
    relevance_path = os.path.join(os.path.dirname(current_path), 'relevance_scores')
    scores = get_query_scores_dict(relevance_path)
    print(scores)


if __name__ == '__main__':
    main()