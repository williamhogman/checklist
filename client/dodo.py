import os
import os.path
import shutil
import noderunner
from doit.tools import create_folder, check_timestamp_unchanged


build_path = "_build"

_nr = None

def get_node():
    global _nr
    if not _nr:
        _nr = noderunner.Client()

    return _nr


def files_in(path, ext=None):
    if ext is not None:
        pred = lambda f: f.endswith(ext)
    else:
        pred = lambda f: True

    ap = os.path.abspath(path)
    for root, dirs, files in os.walk(ap):
        for f in files:
            if pred(f):
                yield os.path.join(root, f)


def rel(p, parts=[]):
    return os.path.relpath(p, os.path.join(os.getcwd(), *parts))


def relcwd(p):
    return rel(p)


def relsrc(p):
    return rel(p, ["src"])


def mkdir_file(targets):
    for target in targets:
        create_folder(os.path.split(target)[0])

def copy_subtask(f, dst):
    return {'task_dep': ['init'],
               'name': relcwd(f),
               'actions': [mkdir_file,
                           (shutil.copyfile, (f, dst))],
               'file_dep': [f],
               'targets': (dst,)                        
               }


def task_init():
    return {'actions': [(create_folder, [build_path]),
                        (create_folder, [os.path.join(build_path, "js")])
                        ],
            'targets': ['_build']}


def cs_compile(srcfile, outfile):
    c = get_node().context("compiler", [("cs", "coffee-script")])

    with open(srcfile) as f:
        code = f.read()

    compiled = c.objects.cs.compile(code)
    with open(outfile, "w") as f:
        f.write(compiled)


def task_compile_cs():
    for f in files_in("src/js", ".coffee"):
        js_p = rel(f, ["src", "js"])
        out_p = os.path.join("_build", "js",
                             os.path.splitext(js_p)[0] + ".js")
        out_dir = os.path.split(out_p)[0]

        yield {'task_dep': ['init'],
               'name': relcwd(f),
               'actions': [mkdir_file,
                           (cs_compile, (f, out_p))],
               'file_dep': [f],
               'targets': (out_p,)
               }


def task_copy_js():
    for f in files_in("src/js", ".js"):
        src = rel(f, ["src", "js"])
        dst = os.path.join("_build", "js", src)

        yield copy_subtask(f, dst)


def task_copy_templates():
    for f in files_in("src/templates", ".html"):
        src = rel(f, ["src", "templates"])
        dst = os.path.join("_build", "templates", src)

        yield copy_subtask(f, dst)


def task_copy_img():
    for f in files_in("src/img", ("png", "jpg", "svg")):
        src = rel(f, ["src", "img"])
        dst = os.path.join("_build", "img", src)

        yield copy_subtask(f, dst)


def task_copy_rootwellknown():
    copy_root = ("robots.txt",
                 "sitemap.xml",
                 "favicon.ico",
                 "humans.txt",
        )
    for f in map("src/well-known/{}".format, copy_root):
        out = os.path.join("_build", rel(f, ["src", "well-known"]))
        yield copy_subtask(f, out)


def task_copy_wellknown():
    for f in files_in("src/well-known"):
        src = rel(f, ["src", "well-known"])
        dst = os.path.join("_build", ".well-known", src)

        yield copy_subtask(f, dst)


def task_copy_index():
    yield copy_subtask("src/index.html", "_build/index.html")


# Left this out for now
def _left_out_task_bower_components():
    return {
        'task_dep': ['init'],
        'targets': ['_build/components'],
#        'uptodate': [check_timestamp_unchanged("components")],
        'actions': ["cp -r components _build/"]
        }
