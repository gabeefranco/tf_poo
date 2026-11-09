import csv
import re
import sys


def remove_padding(value: str) -> str:
    return re.sub(
        r'^([A-Z]+)0+([0-9]+)$',
        lambda match: match.group(1) + str(int(match.group(2))),
        value
    )


def process_csv(input_file: str, output_file: str):
    with open(input_file, 'r', newline='', encoding='utf-8-sig') as infile:
        sample = infile.read(8192)
        infile.seek(0)

        dialect = csv.Sniffer().sniff(sample, delimiters=',;\t|')
        reader = csv.reader(infile, dialect)

        with open(output_file, 'w', newline='', encoding='utf-8') as outfile:
            writer = csv.writer(outfile, dialect)

            for row in reader:
                writer.writerow([remove_padding(value) for value in row])


if __name__ == '__main__':
    if len(sys.argv) != 3:
        print(f"Usage: python {sys.argv[0]} input.csv output.csv")
        sys.exit(1)

    process_csv(sys.argv[1], sys.argv[2])
