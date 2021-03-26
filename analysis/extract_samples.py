"""
This small script extract a subset of samples from the Podcast
transcript dataset for local development
"""
import os
import os.path as osp
import typing
import logging
import shutil
from tqdm import tqdm

logger = logging.getLogger(__name__)
logger.setLevel(logging.DEBUG)
sh = logging.StreamHandler()
logger.addHandler(sh)


ROOT_DIR = osp.dirname(osp.dirname(osp.realpath(__file__)))

DATA_DIR = "data/podcasts-transcript/spotify-podcasts-2020"

TRANSCRIPTS_DIR = os.path.join(ROOT_DIR, DATA_DIR, 'podcasts-transcripts')

SHOW_IDS = [
    "spotify:show:2Vh5jVI1EdMDeHs3N8XoME",
    "spotify:show:2rPPvOAqUPSB6TqezjVjm5",
    "spotify:show:4lbnMhRAmkGBxpGOb6yFAx",
    "spotify:show:3yug4kwkuzzHOAdMe4ecpv",
    "spotify:show:6r0bfT6lOLqDMh9ujnT1BW",
    "spotify:show:2Mkrfx6HroyJiFjCnSRkOF",
    "spotify:show:6ec9hDE62HkW7ckm2KC17L",
    "spotify:show:1ZKHflAANeLU5C4XZ1Aa2O",
    "spotify:show:5rlxB3eLnxgYzXFG8jKfel",
    "spotify:show:4zwERdBdLsB57sSLU7dlFR",
    "spotify:show:6CodmnLnIHc3MpzOOC92ml",
    "spotify:show:22jqRD61y51WewOLvmHxfX",
    "spotify:show:76bHN0zmR0ixLaledqBWrD",
    "spotify:show:1mxdkoSv6URRpLtxMtAFp2",
    "spotify:show:6aJTGYCYhFqIBsaNDPgiac",
]

METADATA_FNAME = "metadata.tsv"

NO_OF_SAMPLE_SHOWS = len(SHOW_IDS)


def extract_sample_metadata() -> None:
    """Extract shows and episodes metadata for all shows in
    SHOW_IDS
    """
    m_file = osp.join(ROOT_DIR, DATA_DIR, METADATA_FNAME)
    sample_mfile = osp.join(ROOT_DIR, 'data', 'metadata_samples.tsv')
    
    orig_f = open(m_file, 'r')
    sample_f = open(sample_mfile, 'w')

    header_line = next(orig_f)
    sample_f.write(header_line) 

    count = 0
    for source_line in orig_f:
        # Get the show uri
        show_uri = source_line.strip().split('\t')[0]
        if show_uri in SHOW_IDS:
            sample_f.write(source_line)
            count += 1

    logger.debug(f'Wrote {count} lines for {NO_OF_SAMPLE_SHOWS} shows')

    orig_f.close()
    sample_f.close()
    

def extract_sample_json_files() -> None:
    """
    """
    # Get only the show id
    show_idx = [sid.replace('spotify:show:', '') for sid in SHOW_IDS]

    # Build a list of absolute paths of all the json transcript files
    json_files = []
    for root, d_names, f_names in os.walk(TRANSCRIPTS_DIR):
        for fn in f_names:
            json_files.append(os.path.join(root, fn))

    logger.debug(f'Got {len(json_files)} JSON transcript files')

    # For each show in `show_idx`, collect the corresponding transcript files
    show_json_files = []
    for s_id in show_idx:
        show_json_files.extend([jsf for jsf in json_files if s_id in jsf])

    logger.debug(f'After filtered, got {len(show_json_files)} JSON transcript files')

    for tjson_f in tqdm(show_json_files):
        # Get the show taxonomy path
        path = tjson_f.replace(f'{TRANSCRIPTS_DIR}/', '')  # Remove the prefix abs path
        path_comps = path.split('/')
        json_fname = path_comps[-1]
        path_comps = path_comps[0:len(path_comps)-1]

        dir_path = os.path.join(ROOT_DIR, 'data', 'podcasts-transcripts-samples', *path_comps)
        if not os.path.isdir(dir_path):
            os.makedirs(dir_path)

        shutil.copyfile(tjson_f, os.path.join(dir_path, json_fname))

if __name__ == '__main__':
    extract_sample_metadata()
    extract_sample_json_files()

