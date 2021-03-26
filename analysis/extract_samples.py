"""
This small script extract a subset of samples from the Podcast
transcript dataset for local development
"""
import os
import os.path as osp
import typing
import logging

logger = logging.getLogger(__name__)
logger.setLevel(logging.DEBUG)
sh = logging.StreamHandler()
logger.addHandler(sh)


ROOT_DIR = osp.dirname(osp.dirname(osp.realpath(__file__)))

DATA_DIR = "data/podcasts-transcript/spotify-podcasts-2020"

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
    sample_f = open(sample_mfile, 'a')

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
    

if __name__ == '__main__':
    extract_sample_metadata()
