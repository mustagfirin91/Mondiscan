import os
import re

def fix_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()
    
    # Replace .androidx.compose... with . for Modifier chains
    # Specifically looking for .androidx.compose.foundation.layout.size, .androidx.compose.foundation.border, etc.
    content = re.sub(r'\.androidx\.compose\.foundation\.layout\.size', r'.size', content)
    content = re.sub(r'\.androidx\.compose\.foundation\.border', r'.border', content)
    content = re.sub(r'\.androidx\.compose\.foundation\.clickable', r'.clickable', content)
    content = re.sub(r'\.androidx\.compose\.ui\.draw\.clip', r'.clip', content)
    
    with open(filepath, 'w') as f:
        f.write(content)

for root, _, files in os.walk('app/src/main/java/com/example'):
    for file in files:
        if file.endswith('.kt'):
            fix_file(os.path.join(root, file))
